import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  showingMenu = false;

  changeMenuState() {
    this.showingMenu = !this.showingMenu;
  }
}
