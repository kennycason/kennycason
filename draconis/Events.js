function Event() {
	this.isDrawable = 0;
	this.isFinished = false;
	this.sprite = null;
	this.name = "NONE";
}

Event.prototype = new Entity();
Event.prototype.constructor = Event;

Event.prototype.handle = function() {

}

function WarpEvent(opt) {
	Event.call(this);
	this.hitRate = 50;
	this.x = opt.x;
	this.y = opt.y;

	this.dest = opt.dest;
	this.destX = opt.destX;
	this.destY = opt.destY;
}
WarpEvent.prototype = new Event();
WarpEvent.prototype.constructor = WarpEvent;

WarpEvent.prototype.handle = function(world) {
	if(this.x == world.player.x 
			&& this.y == world.player.y 
			&& world.player.justMoved) {
		world.level = LevelLoader.load(this.dest, world);
		world.player.x = this.destX;
		world.player.y = this.destY;

	}
}

function LockedDoorEvent(opt) {
	Event.call(this);
	this.hitRate = 50;
	this.x = opt.x;
	this.y = opt.y;

}
LockedDoorEvent.prototype = new Event();
LockedDoorEvent.prototype.constructor = LockedDoorEvent;

LockedDoorEvent.prototype.handle = function(world) {
	if(this.x == world.player.x 
			&& this.y == world.player.y - 1
			&& world.player.justMoved) {
			var has = false;
			for(var i = 0; i < world.player.items.length; i++) {
				if(world.player.items[i].item.name == "SMALL KEY") {
					world.player.items[i].quantity--;
					if(world.player.items[i].quantity <= 0) {
						world.player.items.splice(1, 1);
					}
					has = true;
				}
			}
			if(has) { // use key
				$("#msg").html("Used a SMALL KEY to unlock door!");
				world.level.tiles[this.y][this.x] = 0; // empty block
				world.level.collision[this.y][this.x] = 0; // remove collision tile
			}
	}
}


function ChestEvent(opt) {
	Event.call(this);
	this.hitRate = 50;
	this.x = opt.x;
	this.y = opt.y;

	this.item = opt.item
}
ChestEvent.prototype = new Event();
ChestEvent.prototype.constructor = ChestEvent;

ChestEvent.prototype.handle = function(world) {
	if(this.x == world.player.x 
			&& this.y == world.player.y - 1) {
		this.isFinished = true;
		world.level.tiles[this.y][this.x] = 6; // open chest tile

		// add item to inventory
		if(this.item != null) {
			if(this.item.name == "GOLD") {
				this.item.use(world);
				return;
			}
			$("#msg").html("Found a " + this.item.name);
			var has = false;
			for(var i = 0; i < world.player.items.length; i++) {
				if(world.player.items[i].item.name == this.item.name) {
					world.player.items[i].quantity++;
					has = true;
				}
			}
			if(!has) {
				world.player.items[world.player.items.length] = {item : this.item, quantity : 1};
			}
		}
	}
}


function Boss1Event(opt) {
	Event.call(this);
	this.hitRate = 50;
	this.x = opt.x;
	this.y = opt.y;

	this.sprite = Palette.player;
	this.sprite.x = this.x;
	this.sprite.y = this.y;
	this.isDrawable = true;
}
Boss1Event.prototype = new Event();
Boss1Event.prototype.constructor = Boss1Event;

Boss1Event.prototype.handle = function(world) {
	if(world.player.y <= this.y + 1
			&& world.player.justMoved) {
		$("#msg").html("killed the boss");
		this.isFinished = true;

	}
}
