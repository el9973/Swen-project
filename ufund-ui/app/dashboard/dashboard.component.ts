import { Component, OnInit, EventEmitter, Input, Output} from '@angular/core';
import { Router } from '@angular/router';
import { Helper } from '../helper';
import { HelperService } from '../helper.service';
import { Hero } from '../hero';
import { HeroService } from '../hero.service';
import { Needs } from '../needs_array';
import { SharedService } from '../shared/shared.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  helpers: Helper[] = [];
  id: number[] = [];
  constructor(private shared: SharedService, private router: Router,private heroService: HeroService) { }

  ngOnInit(): void {
    this.id = this.shared.getIDS();
    localStorage.setItem(this.shared.getName(), JSON.stringify(this.shared.getIDS));
  }
  login(name: string): void {
    name = name.trim();
    //this.helperService.getHelperNo404(name).subscribe(helpers => this.helpers = helpers);
    let ids: number[] = new Array();
    
    if (!name) { return; }
    else if(name == 'admin'){
      this.router.navigate(['/heroes']);
    }
    else if(!localStorage.getItem(name)){
      localStorage.setItem(name, JSON.stringify(ids));
      this.shared.setName(name);
      this.shared.setIDS(ids);
      this.router.navigate(['/helperUI']);
    }
    else{
      this.shared.setName(name);
      this.shared.setIDS(this.id);
      this.router.navigate(['/helperUI']);
    }
  }

}


/*
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at https://angular.io/license
*/