$(document).ready(function(){
	console.log('Dom Ready');
	$('select[name="product-category"]').change(function(){
			console.log('Select event tracked');
			var categoryVal = $(this).val();
			var currNode = $(this).attr('data-currNode');
			console.log('categoryVal '+ categoryVal);
			console.log('currNode '+ currNode);
			if(currNode){
				var reloadURL = '';
				if(categoryVal){
					reloadURL = currNode + ".result.category-" + categoryVal + ".html";
				}
				else{
					reloadURL = currNode + ".result.html";
				}
				console.log('reloadURL'+reloadURL);
				$(this).parents('.product-selector').find('.product-result').load(reloadURL);
			}
	});
});