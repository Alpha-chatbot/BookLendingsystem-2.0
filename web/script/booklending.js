var booklending={
		//封装相关ajax的url
		URL:{
			lending:function(bookId,studentId){
				return '/books/'+bookId+'/lending?studentId='+studentId;
			},
			returning:function(bookId,studentId){
				return '/books/'+bookId+'/returning?studentId='+studentId;
			},

			verify:function(){
				return '/books'+'/verify';
			}
		},
		
		//验证学号和密码
		validateStudent:function(studentId,password){
			console.log("studentId"+studentId);
			if(!studentId||!password){
				return "nothing";
			}else if(studentId.length!=10 ||isNaN(studentId)||password.length!=6 ||isNaN(password)){
				return "typerror";
			}else {
				if(booklending.verifyWithDatabase(studentId, password)){
					console.log("验证成功！");
					return "success";
				}else{
					console.log("验证失败！");
					return "mismatch";
				}
			}  
		},
		//将学号和用户名与数据库匹配
		verifyWithDatabase:function(studentId,password){
			var result=false;
			var params={};
			params.studentId=studentId;
			params.password=password;
			console.log("params.password:"+params.password);
			var verifyUrl=booklending.URL.verify();
			$.ajax({
				type:'post',
				url:verifyUrl,
				data:params,
				datatype:'json',
				async:false,                       //同步调用，保证先执行result=true,后再执行return result;
				success:function(data){
					if(data.result=='SUCCESS'){
						window.location.reload();
						//弹出登录成功！
						alert("登陆成功！");
						result=true;
					}else{
						result=false;
					}
				}
			});
			console.log("我是验证结果："+result);
			return result;
			
		},
		
		//预定图书逻辑
		detail:{
			//预定也初始化
			init:function(params){
				var bookId=params['bookId']; 
				console.log("我是js文件！");
				
				var studentId=$.cookie('studentId');
				var password=$.cookie('password');
				if(!studentId||!password){
					//设置弹出层属性
					var  IdAndPasswordModal=$('#varifyModal');
					IdAndPasswordModal.modal({
						show: true,//显示弹出层
	                    backdrop: 'static',//禁止位置关闭
	                    keyboard: false//关闭键盘事件
					});
					$('#studentBtn').click(function (){
						studentId=$('#studentIdKey').val();
							console.log("studentId:"+studentId);
						password=$('#passwordKey').val();
							console.log("password:"+password);
						//调用validateStudent函数验证用户id和密码。
						var temp=booklending.validateStudent(studentId,password);
						console.log(temp);
						if(temp=="nothing"){
							$('#studentMessage').hide().html('<label class="label label-danger">学号或密码为空!</label>').show(300);
						}else if(temp=="typerror"){
							$('#studentMessage').hide().html('<label class="label label-danger">格式不正确!</label>').show(300);
						}else if(temp=="mismatch"){
							console.log("已经调用验证函数！");
							$('#studentMessage').hide().html('<label class="label label-danger">学号密码不匹配!</label>').show(300);
						}else if(temp=="success"){
							 //学号与密码匹配正确，将学号密码保存在cookie中。不设置cookie过期时间，这样即为session模式，关闭浏览器就不保存密码了。
							$.cookie('studentId', studentId, {  path: '/books'}); 
							$.cookie('password', password, {  path: '/books'});

							// 跳转到还书逻辑
							var returningbox=$('#returning-box');
							booklending.returning(bookId,studentId,returningbox);
							// 跳转到预约逻辑
							var lendingbox=$('#lending-box');
							booklending.lending(bookId,studentId,lendingbox);

						}
					}); 
				}else{
					var lendingbox=$('#lending-box');
					booklending.lending(bookId,studentId,lendingbox);

					var returningbox=$('#returning-box');
					booklending.returning(bookId,studentId,returningbox);
				} 
			}	
		},
		lending:function(bookId,studentId, node){
			console.log("我执行预约的方法!" );
			node.html('<button class="btn btn-primary btn-lg" id="lendingBtn">预约</button>');
			  
			var lendingUrl = booklending.URL.lending(bookId,studentId);
			console.log("lendingUrl:"+lendingUrl);
			//绑定一次点击事件
			$('#lendingBtn').one('click', function () {
				//执行预约请求
				//1、先禁用请求
				$(this).addClass('disabled');
				//2、发送预约请求执行预约
				$.post(lendingUrl,{},function(result){   //Ajax强大之处，向Controller方法提出请求和返回结果在一处!
					if(result && result['success']){         //同时还可以连续取对象的子对象！
						var lendingResult=result['data'];
							console.log("lendingResult"+lendingResult);
						var state=lendingResult['state'];
							console.log("state"+state);
						var stateInfo=lendingResult['stateInfo'];
							console.log("stateInfo"+stateInfo);
						//显示预约结果                                                          把结果显示给页面，完成了jsp的工作
							 
						node.html('<span class="label label-success">'+stateInfo+'</span>');
					}       //因为公用一个node所以，用来显示“stateInfo”时就不会显示上面的“预约”
					console.log('result'+result);
				});
			 });
			
			
		},


	returning:function(bookId,studentId, node2){
		console.log("我执行还书的方法!" );
		node2.html('<button class="btn btn-primary btn-lg" id="returningBtn">还书</button>');

		var returningUrl = booklending.URL.returning(bookId,studentId);
		console.log("returningUrl:"+returningUrl);
		//绑定一次点击事件
		$('#returningBtn').one('click', function () {
			//执行还书请求
			//1、先禁用请求
			$(this).addClass('disabled');
			//2、发送还书请求执行还书操作
			$.post(returningUrl,{},function(result){
				if(result && result['success']){
					/*var returningResult=result['data'];
					console.log("returningResult"+returningResult);
					var state=returningResult['state'];
					console.log("state"+state);
					var stateInfo=returningResult['stateInfo'];
					console.log("stateInfo"+stateInfo);*/
					var state1="还书成功";
					//显示还书结果

					node2.html('<span class="label label-success">'+state1+'</span>');
				}
				console.log('result'+result);
			});
		});


	}
}
