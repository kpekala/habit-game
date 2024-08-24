import { Component, DestroyRef, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, NavigationEnd, NavigationStart, Router } from '@angular/router';
import { filter, switchMap } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { HeaderService } from './header.service';
import { UserService } from '../player/user.service';
import { UserResponse } from '../player/user.model';
import { NgIf } from '@angular/common';
import { UserStoreService } from '../player/user.store.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { HeaderStoreService } from './header.store.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    standalone: true,
    imports: [NgIf]
})
export class HeaderComponent implements OnInit {
  showingMenu = false;
  user?: UserResponse;
  @ViewChild('menu') menu: ElementRef;
  @ViewChild('menuIcon') menuIcon: ElementRef;
  readyForClickOutside = false;

  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthService,
      private userService: UserService, private readonly destroyRef: DestroyRef, 
      private readonly headerStoreService: HeaderStoreService) {
  }

  ngOnInit(): void {
    this.headerStoreService.updateHeader$
      .pipe(
        switchMap(() => this.userService.fetchUserInformation()),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: (user) => {
          this.user = user;
        }
    });
    this.headerStoreService.update();
  }

  @HostListener('document:click', ['$event'])
  clickOut(event) {
    if(!this.menu) {
      return;
    }
    if(!this.menu.nativeElement.contains(event.target) && !this.menuIcon.nativeElement.contains(event.target)) {
      this.showingMenu = false;
    }
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
        break;
      case 'shop':
        this.router.navigate(['./shop'], {relativeTo: this.route});
    }
  }

  onLogoutClick() {
    this.authService.logout();
  }
}
