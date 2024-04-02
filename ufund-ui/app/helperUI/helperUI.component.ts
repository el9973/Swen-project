import { Component, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { HeroService } from '../hero.service';
import { SharedService } from '../shared/shared.service';
import { interval } from 'rxjs';

@Component({
  selector: 'app-helperUI',
  templateUrl: './helperUI.component.html',
  styleUrls: [ './helperUI.component.css' ]
})
export class HelperUIComponent implements OnInit {
  name : string = '';
  heroes: Hero[] = [];
  needs: Hero[] = [];
  ids: number[] = [];
  run: number = 0;
  hero: Hero = {
    name: '',
    animal: '',
    cost: 0,
    quantity: 0,
    id: 0
  }
  constructor(private heroService: HeroService, private shared: SharedService) { }

  ngOnInit(): void {
    this.name = this.shared.getName();
    this.ids = this.shared.getIDS();
    this.getHeroes();
    if(this.ids.length != 0){
      this.getNeed();
    }
  }
  getNeed(): void {
    this.needs = new Array();
      for (let i = 0; i < this.ids.length ; i++) {
        let item = this.ids[i];
        this.heroService.getHero(item)
      .subscribe(heroes => this.needs.push(heroes));
      }
  }
  getHeroes(): void {
    this.heroService.getHeroes()
    .subscribe(heroes => this.heroes = heroes);
    
  }
  delete(item:number): void {
    const index = this.ids.findIndex(number => number === item);
    this.ids.splice(index, 1);
    this.getNeed();
  }
  add(item: number): void{
    //this.heroes.push(hero);
    this.heroService.getHero(item)
    .subscribe(heroes => {this.needs.push(heroes); this.ids.push(heroes.id); this.shared.setIDS(this.ids)});

  }
  submit(): void{
    const len = this.needs.length;
    let map = new Map<number, number>(); 
    let number = 0;
    for(let i = 0; i < len; i++){
        if(map.get(this.needs[i].id)=== undefined){
          map.set(this.needs[i].id, this.needs[i].quantity + 1);
        }
        else{
          map.set(this.needs[i].id, map.get(this.needs[i].id) as number + 1);
          this.run = this.run + 1;
        }
        this.hero = {
          name: this.needs[i].name,
          animal: this.needs[i].animal,
          cost: this.needs[i].cost,
          quantity: map.get(this.needs[i].id) as number,
          id: this.needs[i].id
        };
        this.run = this.hero.quantity;
        this.heroService.updateHero(this.hero)
        .subscribe();
    }
    this.ids = [];
    this.needs = [];
    this.getHeroes();
  }



  
}