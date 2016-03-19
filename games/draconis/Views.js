function Stats(world) {
	this.world = world;
	this.player = this.world.player;
}

Stats.prototype = new Entity();
Stats.prototype.constructor = Stats;

Stats.prototype.handle = function() {
	if(this.world.keyboard.isKeyPressed(Keys.ESCAPE)) {
		this.world.gs = GS.MAIN;
	}
}

Stats.prototype.draw = function(screen) {

    var context = screen.context;
    // save state
    context.save();

    context.translate(20, 20);
    context.fillStyle = Palette.BLACK.getHex();
    context.fillRect(0, 0, 584, 440);  

    context.translate(5, 5);
    context.fillStyle = Palette.WHITE.getHex();
    context.fillRect(0, 0, 574, 430);  
    context.restore();

	screen.drawText("DRACO - KNIGHT", 35, 50, Palette.colors[1], "bold 16px Comic San");

	screen.drawText("LEVEL " + this.player.level, 35, 75, Palette.colors[1], "bold 16px Comic San");
	screen.drawText("XP " + this.player.xp, 35, 100, Palette.colors[1], "bold 16px Comic San");
	screen.drawText("GOLD " + this.player.gp, 35, 125, Palette.colors[1], "bold 16px Comic San");

	screen.drawText("STR " + this.player.str, 35, 175, Palette.colors[1], "bold 16px Comic San");
	screen.drawText("DEX " + this.player.dex, 35, 200, Palette.colors[1], "bold 16px Comic San");
	screen.drawText("CON " + this.player.con, 35, 225, Palette.colors[1], "bold 16px Comic San");
	screen.drawText("INT " + this.player.iq, 35, 250, Palette.colors[1], "bold 16px Comic San");


	screen.drawText("HP " + this.player.hp + " / " + this.player.hpm, 300, 75, Palette.colors[1], "bold 16px Comic San");
	screen.drawText("MP " + this.player.mp + " / " + this.player.mpm, 300, 125, Palette.colors[1], "bold 16px Comic San");

	screen.drawText("WEAPON", 300, 175, Palette.colors[1], "bold 16px Comic San");

	if(this.player.weapon != null) {
		screen.drawText(this.player.weapon.name, 300, 200, Palette.colors[1], "bold 16px Comic San");
		screen.drawText("DMG " + this.player.weapon.dMin + "-" + this.player.weapon.dMax 
			+ "    ACC " + this.player.weapon.hitRate + "%", 300, 225, Palette.colors[1], "bold 16px Comic San");
	} else {
		screen.drawText("NONE", 300, 200, Palette.colors[1], "bold 16px Comic San");
	}

	screen.drawText("ARMOR", 300, 275, Palette.colors[1], "bold 16px Comic San");
	if(this.player.armor != null) {
		screen.drawText(this.player.armor.name, 300, 300, Palette.colors[1], "bold 16px Comic San");
		screen.drawText("DEF +" + this.player.armor.def , 300, 325, Palette.colors[1], "bold 16px Comic San");
	} else {
		screen.drawText("NONE", 300, 300, Palette.colors[1], "bold 16px Comic San");
	}
}





function Inventory(world) {
	this.world = world;
	this.player = this.world.player;
	this.selected = 0;
	this.lastMoved = Clock.time();
	this.lastUsed = Clock.time();
}

Inventory.prototype = new Entity();
Inventory.prototype.constructor = Inventory;


Inventory.prototype.handle = function() {
	if(Clock.time() - this.lastMoved > 50) {
		this.lastMoved = Clock.time();
		if(this.world.keyboard.isKeyPressed(Keys.DOWN) || this.world.keyboard.isKeyPressed(Keys.S)) {
			this.selected = (this.selected + 1) % this.player.items.length;
		}
		if(this.world.keyboard.isKeyPressed(Keys.UP) || this.world.keyboard.isKeyPressed(Keys.W)) {
			this.selected = this.selected - 1;
			if(this.selected < 0) {
				this.selected = this.player.items.length - 1;
			}
		}
	}
	if(Clock.time() - this.lastUsed > 50) {
		this.lastUsed = Clock.time();
		if(this.world.keyboard.isKeyPressed(Keys.U)) {
			if(this.player.items[this.selected].quantity > 0) {
				this.player.items[this.selected].item.use(this.world);

				this.player.items[this.selected].quantity--;
				if(this.player.items[this.selected].quantity == 0) {
					this.player.items.splice(this.selected, 1);
					this.selected = (this.selected + 1) % this.player.items.length;
				}
			}
		}
	}

	if(this.world.keyboard.isKeyPressed(Keys.ESCAPE)) {
		this.world.gs = GS.MAIN;
	}
}

Inventory.prototype.draw = function(screen) {

    var context = screen.context;
    // save state
    context.save();

    context.translate(20, 20);
    context.fillStyle = Palette.BLACK.getHex();
    context.fillRect(0, 0, 584, 440);  

    context.translate(5, 5);
    context.fillStyle = Palette.WHITE.getHex();
    context.fillRect(0, 0, 574, 430);  

    context.restore();

    screen.drawText("INVENTORY", 35, 50, Palette.colors[1], "bold 16px Comic San");

    if(this.selected >= this.player.items.length) {
    	this.selected = 0;
    }
    for(var i = 0; i < this.player.items.length; i++) {
    	if(this.selected == i) {
    		context.save();
		    context.translate(35, 65 + (25 * i));
		    context.fillStyle = Palette.BLACK.getHex();
		    context.fillRect(0, 0, 8, 4);  
		    context.restore();
    	}
    	screen.drawText(this.player.items[i].item.name + " x" + this.player.items[i].quantity, 50, 75 + (25 * i), Palette.colors[1], "bold 16px Comic San");
    }

	
}
