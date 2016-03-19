function Enemy(world) {
	Entity.call(this);
	this.world = world;

	this.hp = 1;
	this.hpm = 1;
	this.name = "None";
	this.score = 50;

}

Enemy.prototype = new Entity();
Enemy.prototype.constructor = Enemy;

Enemy.prototype.handle = function() {
	if(this.invincible) {
		var time = Clock.time();
		if(time - this.lastHit > 200) {
			this.invincible = false;
		}
	}
}

Enemy.prototype.hit = function(weapon) {

}

Enemy.prototype.attack = function() {
	alert('default enemy attack()');
}

Enemy.prototype.draw = function() {
	alert('default enemy draw()');
}

Enemy.prototype.die = function() {
	alert('default enemy die()');
}

Enemy.prototype.dead = function() {
	alert('default enemy dead()');
}


