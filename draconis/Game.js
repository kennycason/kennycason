function Game() {

	this.keyboard = new Keyboard();
	this.mouse = new Mouse();

	this.screen = new Canvas();

	this.player = new Player(this);

	this.level = LevelLoader.load(0, this);
	this.player.x = 2;
	this.player.y = 6;

	this.stats = new Stats(this);
	this.inventory = new Inventory(this);

	this.gs = GS.MAIN;

	this.paused = false;

	this.gameloopInterval;

	this.run = function() {
		this.initKeyboard();
		var that = this;
		var FPS = 30;
		//window.addEventListener('focus', function() {
			that.gameloopInterval = setInterval(function() {
				if(!that.paused) {
					that.handle();
					that.draw();	
				}
			}, 33);
		//});    
	/*	window.addEventListener('blur', function() {
			window.clearInterval(that.gameloopInterval);
		});*/
	}

	this.handle = function() {
		switch(this.gs) {
			case GS.MAIN:
				this.level.handle();
				break;
			case GS.STATS:
				this.stats.handle();
				break;	
			case GS.INVENTORY:
				this.inventory.handle();
				break;	
		}
	}

	this.draw = function() {
	    var context = this.screen.context;
	    // save state
	    context.save();
	    context.fillStyle = Palette.WHITE.getHex();
	    context.fillRect(0, 0, 624, 480); 
	    context.restore(); 

		switch(this.gs) {
			case GS.MAIN:
				this.level.draw(this.screen);
				break;
			case GS.STATS:
				this.level.draw(this.screen);
				this.stats.draw(this.screen);
				break;		
			case GS.INVENTORY:
				this.level.draw(this.screen);
				this.inventory.draw(this.screen);
				break;	
		}
	}

	this.initKeyboard = function() {
		// bind clicks
		var that = this;
		this.keyboard.onKeyPressed(Keys.ENTER, function() {
			window.location.reload();
		});
		this.keyboard.onKeyPressed(Keys.P, function() {
			that.paused = !that.paused;
		});

	}

}

GS = {}
GS.MAIN = 1;
GS.STATS = 2;
GS.INVENTORY = 3;
GS.BATTLE = 4;

Clock = {

	time : function() {
		return new Date().getTime();
	}
}