function Entity() {
	this.x = 0;
	this.y = 0;
	this.w = 0;
	this.h = 0;
}

Entity.prototype.locate = function(x, y) {
	this.x = x;
	this.y = y;
}
