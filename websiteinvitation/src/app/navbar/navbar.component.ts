import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  constructor() {}
  check: any = 0;
  onKey() {
    if (this.check != 0) {
      this.check = 0;
    } else if (this.check != 1) {
      this.check = 1;
    }
    console.log('test');
  }
  ngOnInit(): void {}
}
