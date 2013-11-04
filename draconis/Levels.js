var Levels = [
	{ // 0
		tiles : [
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
			[1,5,0,0,0,1,1,1,0,0,0,0,1],
			[1,0,0,0,0,1,1,1,0,0,0,0,1],
			[1,0,0,1,0,1,1,1,0,1,0,0,1],
			[1,0,0,1,0,1,2,1,0,1,0,0,1],
			[1,0,0,1,0,1,7,1,0,1,0,0,1],
			[1,0,0,1,0,1,0,1,0,1,0,0,1],
			[1,0,0,1,0,0,0,0,0,1,0,0,1],
			[1,0,0,1,0,0,0,0,0,1,0,2,1],
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
		],
		collision : [
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
			[1,1,0,0,0,1,1,1,0,0,0,0,1],
			[1,0,0,0,0,1,1,1,0,0,0,0,1],
			[1,0,0,1,0,1,1,1,0,1,0,0,1],
			[1,0,0,1,0,1,0,1,0,1,0,0,1],
			[1,0,0,1,0,1,1,1,0,1,0,0,1],
			[1,0,0,1,0,1,0,1,0,1,0,0,1],
			[1,0,0,1,0,0,0,0,0,1,0,0,1],
			[1,0,0,1,0,0,0,0,0,1,0,0,1],
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
		],
		events : [
			new WarpEvent({x : 11, y : 8, dest : 1, destX : 11, destY : 8}),
			new ChestEvent({x : 1, y : 1, item : new Potion()}),
			new LockedDoorEvent({x : 6, y : 5}),
			new WarpEvent({x : 6, y : 4, dest : 3, destX : 6, destY : 8}),
		],
		battleChance : 10,
		monsterSet : 1
	},
	{ // 1
		tiles : [
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
			[1,2,0,0,0,0,0,0,0,0,0,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,0,0,0,0,0,0,0,0,0,3,1],
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
		],
		collision : [
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
			[1,0,0,0,0,0,0,0,0,0,0,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,0,0,0,0,0,0,0,0,0,0,1],
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
		],
		events : [
			new WarpEvent({x : 1, y : 1, dest : 2, destX : 1, destY : 1}), 
			new WarpEvent({x : 11, y : 8, dest : 0, destX : 11, destY : 8})
		],
		battleChance : 10,
		monsterSet : 1
	},
	{ // 2
		tiles : [
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
			[1,3,4,4,4,4,4,4,4,4,4,5,1],
			[1,0,4,4,4,4,4,4,4,4,4,0,1],
			[1,0,4,4,4,4,4,4,4,4,4,0,1],
			[1,0,4,4,4,4,4,4,4,4,4,0,1],
			[1,0,4,4,4,4,4,4,4,4,4,0,1],
			[1,0,4,4,4,4,4,4,4,4,4,0,1],
			[1,0,4,4,4,4,4,4,4,4,4,0,1],
			[1,0,0,0,0,0,0,0,0,0,0,0,1],
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
		],
		collision : [
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
			[1,0,1,1,1,1,1,1,1,1,1,1,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,1,1,1,1,1,1,1,1,1,0,1],
			[1,0,0,0,0,0,0,0,0,0,0,0,1],
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
		],
		events : [
			new WarpEvent({x : 1, y : 1, dest : 1, destX : 1, destY : 1}), 
			new ChestEvent({x : 11, y : 1, item : new SmallKey()})
		],
		battleChance : 10,
		monsterSet : 1
	},
	{ // 3
		tiles : [
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
			[1,4,4,4,4,5,5,5,4,4,4,4,1],
			[1,4,4,4,4,0,0,0,4,4,4,4,1],
			[1,4,4,4,4,0,0,0,4,4,4,4,1],
			[1,4,4,4,4,0,0,0,4,4,4,4,1],
			[1,4,4,4,4,0,0,0,4,4,4,4,1],
			[1,4,4,4,4,0,0,0,4,4,4,4,1],
			[1,4,4,4,4,0,0,0,4,4,4,4,1],
			[1,4,4,4,4,0,3,0,4,4,4,4,1],
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
		],
		collision : [
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
			[1,0,0,0,1,1,1,1,1,0,0,0,1],
			[1,0,0,0,1,0,0,0,1,0,0,0,1],
			[1,0,0,0,1,0,0,0,1,0,0,0,1],
			[1,0,0,0,1,0,0,0,1,0,0,0,1],
			[1,0,0,0,1,0,0,0,1,0,0,0,1],
			[1,0,0,0,1,0,0,0,1,0,0,0,1],
			[1,0,0,0,1,0,0,0,1,0,0,0,1],
			[1,0,0,0,1,0,0,0,1,0,0,0,1],
			[1,1,1,1,1,1,1,1,1,1,1,1,1],
		],
		events : [
			new WarpEvent({x : 6, y : 8, dest : 0, destX : 6, destY : 4}), 
			new ChestEvent({x : 5, y : 1, item : new Gold(300)}),
			new ChestEvent({x : 6, y : 1, item : new StrPill()}),
			new ChestEvent({x : 7, y : 1, item : new HiPotion()}),
			new Boss1Event({x : 6, y : 2})
		],
		battleChance : 10,
		monsterSet : 1
	},

]