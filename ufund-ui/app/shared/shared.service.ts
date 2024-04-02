import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  name:string = '';
  ids:number[] = [];
  constructor() { }
  setName(data: string){
    this.name = data;
  }
  getName(){
    return this.name;
  }
  setIDS(data: number[]){
    this.ids = data;
  }
  getIDS(){
    return this.ids;
  }
}