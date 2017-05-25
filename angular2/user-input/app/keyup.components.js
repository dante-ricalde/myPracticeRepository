"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var KeyUpComponent_v1 = (function () {
    function KeyUpComponent_v1() {
        this.values = '';
    }
    /*
    onkey(event: any) { // without type info
        this.values += event.target.value + ' | ';

    }*/
    KeyUpComponent_v1.prototype.onkey = function (event) {
        this.values += event.target.value + ' | ';
    };
    KeyUpComponent_v1 = __decorate([
        core_1.Component({
            selector: 'key-up1',
            template: "\n\t\t<input (keyup)=\"onkey($event)\">\n\t\t<p>{{values}}</p>\n\t"
        }), 
        __metadata('design:paramtypes', [])
    ], KeyUpComponent_v1);
    return KeyUpComponent_v1;
}());
exports.KeyUpComponent_v1 = KeyUpComponent_v1;
//////////////////////////// KEY UP 2 ///////////////////////////////////
var KeyUpComponent_v2 = (function () {
    function KeyUpComponent_v2() {
        this.values = '';
    }
    KeyUpComponent_v2.prototype.onKey = function (value) {
        this.values += value + ' | ';
    };
    KeyUpComponent_v2 = __decorate([
        core_1.Component({
            selector: 'key-up2',
            template: "\n\t\t<input #box (keyup)=\"onKey(box.value)\">\n\t\t<p>{{values}}</p>\n\t"
        }), 
        __metadata('design:paramtypes', [])
    ], KeyUpComponent_v2);
    return KeyUpComponent_v2;
}());
exports.KeyUpComponent_v2 = KeyUpComponent_v2;
//////////////////////////// KEY UP 3 ///////////////////////////////////
var KeyUpComponent_v3 = (function () {
    function KeyUpComponent_v3() {
        this.value = '';
    }
    KeyUpComponent_v3.prototype.onEnter = function (value) { this.value = value; };
    KeyUpComponent_v3 = __decorate([
        core_1.Component({
            selector: 'key-up3',
            template: "\n\t\t<input #box (keyup.enter)=\"onEnter(box.value)\">\n\t\t<p>{{value}}</p>\n\t"
        }), 
        __metadata('design:paramtypes', [])
    ], KeyUpComponent_v3);
    return KeyUpComponent_v3;
}());
exports.KeyUpComponent_v3 = KeyUpComponent_v3;
///////////////////////////// KEY UP 4 //////////////////////////////////
var KeyUpComponent_v4 = (function () {
    function KeyUpComponent_v4() {
        this.value = '';
    }
    KeyUpComponent_v4.prototype.update = function (value) { this.value = value; };
    KeyUpComponent_v4 = __decorate([
        core_1.Component({
            selector: 'key-up4',
            template: "\n\t\t<input #box (keyup.enter)=\"update(box.value)\" (blur)=\"update(box.value)\">\n\n\t\t<p>{{value}}</p>\n\t"
        }), 
        __metadata('design:paramtypes', [])
    ], KeyUpComponent_v4);
    return KeyUpComponent_v4;
}());
exports.KeyUpComponent_v4 = KeyUpComponent_v4;
//# sourceMappingURL=keyup.components.js.map