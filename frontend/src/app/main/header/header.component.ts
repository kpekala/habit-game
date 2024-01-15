import { Component } from '@angular/core';
import { ActivatedRoute, NavigationEnd, NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  showingMenu = false;

  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthService) {
  }

  changeMenuState() {
    this.showingMenu = !this.showingMenu;
  }

  onItemClick(item: string) {
    switch(item) {
      case 'tasks': 
        this.router.navigate(['./tasks'], {relativeTo: this.route});
        break;
      case 'player':
        this.router.navigate(['./player'], {relativeTo: this.route});
    }
  }

  onLogoutClick() {
    this.authService.logout();
  }
}
