function Armor(world) {
	Entity.call(this);
	this.world = world;
	this.def = 0;
	this.sprite = null;
	this.name = "NONE";
}

Armor.prototype = new Entity();
Armor.prototype.constructor = Armor;

Armor.prototype.drawIcon = function(screen) {
	alert('default armor draw()');
}

Armor.prototype.use = function(enemy) {

}

function Cloth(world) {
	Weapon.call(this, world);
	this.def = 1;
	this.name = "CLOTH";
}
WoodSword.prototype = new Armor();
WoodSword.prototype.constructor = WoodSword;

function WoodSword(world) {
	Weapon.call(this, world);
	this.def = 2;
	this.name = "LEATHER";
}
WoodSword.prototype = new Armor();
WoodSword.prototype.constructor = WoodSword;
