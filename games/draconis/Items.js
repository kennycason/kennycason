function Item() {
	this.isDrawable = 0;
	this.sprite = null;
	this.name = "NONE";
}

Item.prototype = new Entity();
Item.prototype.constructor = Item;

Item.prototype.use = function() {

}

Item.prototype.draw = function(screen, x, y) {
	this.sprite.draw(screen, x, y);
}

function Potion() {
	Item.call(this);
	this.name = "POTION";
}
Potion.prototype = new Item();
Potion.prototype.constructor = Potion;

Potion.prototype.use = function(world) {
	world.player.hp += 30;
	if(world.player.hp > world.player.hpm) {
		world.player.hp = world.player.hpm;
	}
}

function HiPotion() {
	Item.call(this);
	this.name = "HI POTION";
}
HiPotion.prototype = new Item();
HiPotion.prototype.constructor = HiPotion;

HiPotion.prototype.use = function(world) {
	world.player.hp += 200;
	if(world.player.hp > world.player.hpm) {
		world.player.hp = world.player.hpm;
	}
}

function StrPill() {
	Item.call(this);
	this.name = "STR PILL";
}
StrPill.prototype = new Item();
StrPill.prototype.constructor = StrPill;

StrPill.prototype.use = function(world) {
	world.player.str += 2;
}

function ConPill() {
	Item.call(this);
	this.name = "CON PILL"
}
ConPill.prototype = new Item();
ConPill.prototype.constructor = ConPill;

ConPill.prototype.use = function(world) {
	world.player.con += 2;
}

function HPUp() {
	Item.call(this);
	this.name = "HP UP";
}
HPUp.prototype = new Item();
HPUp.prototype.constructor = HPUp;

HPUp.prototype.use = function(world) {
	if(world.player.hpm < 200) {
		world.player.hpm += 25;
	}
}

function MPUp() {
	Item.call(this);
	this.name = "MP UP";
}
MPUp.prototype = new Item();
MPUp.prototype.constructor = MPUp;

MPUp.prototype.use = function(world) {
	if(world.player.mpm < 200) {
		world.player.mpm += 25;
	}
}

function SmallKey() {
	Item.call(this);
	this.name = "SMALL KEY";
}
SmallKey.prototype = new Item();
SmallKey.prototype.constructor = SmallKey;

SmallKey.prototype.use = function(world) {
}



function Gold(amount) {
	Item.call(this);
	this.name = "GOLD";
	this.amount = amount;
}
Gold.prototype = new Item();
Gold.prototype.constructor = Gold;

Gold.prototype.use = function(world) {
	$("#msg").html("found " + this.amount + "GP!");
	world.player.gp += this.amount;
}

