function Weapon(world) {
	Entity.call(this);
	this.world = world;
	this.hitRate = 0;
	this.dMin = 0;
	this.dMax = 0;
	this.sprite = null;
	this.name = "NONE";
}

Weapon.prototype = new Entity();
Weapon.prototype.constructor = Weapon;

Weapon.prototype.drawIcon = function(screen) {
	alert('default weapon draw()');
}

Weapon.prototype.use = function(enemy) {

}

function Fist(world) {
	Weapon.call(this, world);
	this.hitRate = 50;
	this.dMin = 1;
	this.dMax = 2;
	this.name = "FIST";
}
WoodSword.prototype = new Weapon();
WoodSword.prototype.constructor = WoodSword;

function WoodSword(world) {
	Weapon.call(this, world);
	this.hitRate = 76;
	this.dMin = 2;
	this.dMax = 4;
	this.name = "WOOD SWORD";
}
WoodSword.prototype = new Weapon();
WoodSword.prototype.constructor = WoodSword;
