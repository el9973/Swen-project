import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Hero } from './hero';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const heroes = [
      { id: 12, name: 'Food', cost: 12, animal: 'Cat', quantity: 1},
      { id: 13, name: 'Blanket', cost: 15, animal: 'Dog', quantity: 0},
      { id: 14, name: 'Bowl', cost: 6, animal: 'Parrot', quantity: 12},
      { id: 15, name: 'Dry Grass', cost: 8, animal: 'Hamster', quantity: 1},
      { id: 16, name: 'Oil', cost: 3, animal: 'cat', quantity: 5},
    ];
    return {heroes};
  }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  genId(heroes: Hero[]): number {
    return heroes.length > 0 ? Math.max(...heroes.map(hero => hero.id)) + 1 : 11;
  }
}


/*
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at https://angular.io/license
*/