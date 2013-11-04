
function Player(world) {
	Entity.call(this);
	this.world = world;

	this.x = 1;
	this.y = 1;

	this.hp = 10;
	this.hpm = 20;

	this.mp = 0;
	this.mpm = 0;

	this.level = 1;
	this.xp = 0;
	this.gp = 100;

	this.str = 2;
	this.dex = 2;
	this.con = 2;
	this.iq = 2;

	this.justMoved = false;

	this.armor = new Cloth(world);

	this.weapon = new Fist(world);

	this.items = [
		{item : new Potion(), quantity : 2},
	];

	this.sprite = Palette.player;

	this.lastMoved = Clock.time();
/*
	var that = this;
	$(document).click(function(e) {
		switch(e.which) {
			case 1: // primary
				
				break;
			case 3: // secondary
				
				break;
		}
	});

	$(document).bind("contextmenu", function(e){
	    return false;
	}); */

}

Player.prototype = new Entity();
Player.prototype.constructor = Player;

Player.prototype.isDead = function(damage) {
	return this.hp <= 0
}

Player.prototype.handle = function() {
	this.justMoved = false;
	if(Clock.time() - this.lastMoved > 70) {
		if(this.world.keyboard.isKeyPressed(Keys.LEFT) || this.world.keyboard.isKeyPressed(Keys.A)) {
			if(!this.world.level.collide(this, this.x - 1, this.y)) {
				this.x -= 1;
				this.lastMoved = Clock.time();
				this.justMoved = true;
			}
		}
		if(this.world.keyboard.isKeyPressed(Keys.RIGHT) || this.world.keyboard.isKeyPressed(Keys.D)) {
			if(!this.world.level.collide(this, this.x + 1, this.y)) {
				this.x += 1;
				this.lastMoved = Clock.time();
				this.justMoved = true;
			}
		}
		if(this.world.keyboard.isKeyPressed(Keys.DOWN) || this.world.keyboard.isKeyPressed(Keys.S)) {
			if(!this.world.level.collide(this, this.x, this.y + 1)) {
				this.y += 1;
				this.lastMoved = Clock.time();
				this.justMoved = true;
			}
		}
		if(this.world.keyboard.isKeyPressed(Keys.UP) || this.world.keyboard.isKeyPressed(Keys.W)) {
			if(!this.world.level.collide(this, this.x, this.y - 1)) {
				this.y -= 1;
				this.lastMoved = Clock.time();
				this.justMoved = true;
			}
		}
	}

	if(this.world.keyboard.isKeyPressed(Keys.O)) {
		this.world.gs = GS.STATS;
	} else	if(this.world.keyboard.isKeyPressed(Keys.I)) {
		this.world.gs = GS.INVENTORY;
	}

	// check for random attack
	if(this.justMoved) {

	}
}

Player.prototype.draw = function(screen) {
	this.sprite.draw(screen, this.x * 16, this.y * 16);
}




