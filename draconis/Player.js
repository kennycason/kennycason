
function Player(world) {
	Entity.call(this);
	this.world = world;

	this.x = 10;
	this.y = 6;

	this.vx = 0;
	this.vy = 0;

	this.offX = 0;
	this.offY = 0;

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

	this.moving = false;
	this.moveV = 4;

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

		if(!this.moving) {
			if(this.world.keyboard.isKeyPressed(Keys.LEFT) || this.world.keyboard.isKeyPressed(Keys.A)) {
				if(!this.world.level.collide(this, this.x - 1, this.y)) {
					this.vx =- this.moveV;
					this.lastMoved = Clock.time();
					this.moving = true;
				}
			}
			if(this.world.keyboard.isKeyPressed(Keys.RIGHT) || this.world.keyboard.isKeyPressed(Keys.D)) {
				if(!this.world.level.collide(this, this.x + 1, this.y)) {
					this.vx = this.moveV;
					this.lastMoved = Clock.time();
					this.moving = true;
				}
			}
			if(this.world.keyboard.isKeyPressed(Keys.DOWN) || this.world.keyboard.isKeyPressed(Keys.S)) {
				if(!this.world.level.collide(this, this.x, this.y + 1)) {
					this.vy = this.moveV;
					this.lastMoved = Clock.time();
					this.moving = true;
				}
			}
			if(this.world.keyboard.isKeyPressed(Keys.UP) || this.world.keyboard.isKeyPressed(Keys.W)) {
				if(!this.world.level.collide(this, this.x, this.y - 1)) {
					this.vy = -this.moveV;
					this.lastMoved = Clock.time();
					this.moving = true;
				}
			}
		} 
	}
	if(this.moving) {
		// handle smooth movement
		this.offX += this.vx;
		this.offY += this.vy;
		if(Math.abs(this.vx) > 0 && Math.abs(this.offX) >= 16) {
			this.x += this.vx / 4;
			this.offX = 0;
			this.vx = 0;
			this.moving = false;
			this.justMoved = true;
		}
		if(Math.abs(this.vy) > 0 && Math.abs(this.offY) >= 16) {
			this.y += this.vy / 4;
			this.offY = 0;
			this.vy = 0;
			this.moving = false;
			this.justMoved = true;
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
	this.sprite.draw(screen, this.x * 16 + this.offX, this.y * 16 + this.offY);
}




