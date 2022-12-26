//파라미터 받기
const get_param_by_name = (name) => {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");    
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)")
	var results = regex.exec(location.search);    
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


// object null check
function is_empty_obj(obj)  {
  if(obj.constructor === Object
     && Object.keys(obj).length === 0)  {
    return true;
  }
  
  return false;
}


// 세션 체크
const session_check = () => {
	return new Promise((resolve, reject)=>{
		$.ajax({
			type:'post'
		,	url:'/user/check_sign_in'
		,	contentType:'application/json'
		,	success:function(data){
			if(data.code != "success"){
				resolve(false);
			}
			else{
				resolve(true);
			}
		}
		,	error:function(error){
			console.log(error);
		}
		});
	})
}

/* serializeObject */
 $.fn.serializeObject = function() {
  "use strict"
  var result = {}
  var extend = function(i, element) {
    var node = result[element.name]
    if ("undefined" !== typeof node && node !== null) {
      if ($.isArray(node)) {
        node.push(element.value)
      } else {
        result[element.name] = [node, element.value]
      }
    } else {
      result[element.name] = element.value
    }
  }

  $.each(this.serializeArray(), extend)
  return result
}


