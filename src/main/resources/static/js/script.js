console.log("this is controller file")

const togleSideBar = () =>{
	
	if($(".sideBar").is(":visible")){
		$(".sideBar").css("display" ,"none");
		$(".content").css("margin-left" ,"0%");
	}else{
		$(".sideBar").css("display" ,"block");
		$(".content").css("margin-left" ,"20%");
	}
}