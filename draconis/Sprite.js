function Sprite(data, w, h) {
    this.frames = 1;
    this.data = data;
    this.w = w;
    this.h = h;

    this.scale = 3;
}

Sprite.prototype.draw = function(canvas, x, y) {
    var w = this.w;
    var h = this.h;
    if((x + w/2 < 0) || (x - w/2 > canvas.width)
      || (y + h/2 < 0) || (y - h/2 > canvas.height)) {
        return;
    }

    var context = canvas.context;
    // save state
    context.save();
    // set screen position
    context.translate(x * this.scale, y * this.scale);

    for(var y2 = 0; y2 < h; y2++) {
        for(var x2 = 0; x2 < w; x2++) {
            context.fillStyle = Palette.colors[this.data[x2 + (w * y2)]].getHex();
            //$("#msg").append(this.data[x2][y2] + "<br/>");
            context.fillRect(x2 * this.scale, y2 * this.scale, this.scale, this.scale);  
        } 
    }
    // restore state
    context.restore();
}

function AnimatedSprite(data, w, h, speed, fn) {
    this.frames = data.length;
    this.sprites = [];
    for(var i = 0; i < data.length; i++) {
        this.sprites[i] = new Sprite(data[i], w, h);
    }
    this.currentFrame = 0;
    this.speed = speed;
    this.lastAnimated = 0;
    this.cycled = false;
}
AnimatedSprite.prototype = new Sprite();
AnimatedSprite.prototype.constructor = AnimatedSprite;

AnimatedSprite.prototype.draw = function(canvas, x, y) {
    
    this.sprites[this.currentFrame].draw(canvas, x, y);

    // animate
    if(this.speed == -1) {
        return;
    }
    var time = Clock.time();
    if(time - this.lastAnimated > this.speed) {
        this.lastAnimated = time;
        this.currentFrame += 1;
        if(this.currentFrame >= this.sprites.length) {
            this.currentFrame = 0;
        }
    }
}