function Level(world, data) {
	this.world = world;

	// 13 x 10
	this.tiles = data.tiles;
	this.collision = data.collision;
	this.events = data.events;
	this.battleChance = data.battleChance;
	this.monsterSet = data.monsterSet;

	this.x = 0;
	this.y = 0;

	this.w = 13;
	this.h = 10;

}

Level.prototype = new Entity();
Level.prototype.constructor = Level;

// collide with intraversable entities
Level.prototype.collide = function(e, ex, ey) {
	return this.collision[ey][ex] == 1;
}

// collide with enemies
Level.prototype.isBattle = function() {

	return false;
}

Level.prototype.handle = function() {
	this.world.player.handle();
	for(var i = 0; i < this.events.length; i++) {
		// handle general stuff
		this.events[i].handle(this.world);
		if(this.events[i].isFinished) {
			this.events.splice(i, 1);
			i--;
		}
	}
}

Level.prototype.draw = function(screen) {
	for(var y = 0; y < this.h; y++) {
		for(var x = 0; x < this.w; x++) {
			if(this.tiles[y][x] > 0) {
				Palette.worldSprites[this.tiles[y][x]].draw(screen, x * 16, y * 16);
			}
		}
	}
	this.world.player.draw(screen);
	// draw events
	for(var i = 0; i < this.events.length; i++) {
		if(this.events[i].isDrawable) {
			this.events[i].sprite.draw(screen, this.events[i].x * 16, this.events[i].y * 16);
		}
	}
}

LevelLoader = {};
LevelLoader.load = function(level, world) {
	return new Level(world, Levels[level]);
}
