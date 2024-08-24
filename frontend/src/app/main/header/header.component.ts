import { NgIf } from '@angular/common';
import { Component, DestroyRef, ElementRef, HostListener, OnInit, signal, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { UserResponse } from '../player/user.model';
import { UserService } from '../player/user.service';
import { UserStoreService } from '../player/user.store.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    standalone: true,
    imports: [NgIf]
})
export class HeaderComponent implements OnInit {
  showingMenu = false;
  user = signal(null);
  @ViewChild('menu') menu: ElementRef;
  @ViewChild('menuIcon') menuIcon: ElementRef;
  readyForClickOutside = false;

  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthService,
      private userService: UserService, private readonly destroyRef: DestroyRef, private readonly userStoreService: UserStoreService) {
  }

  ngOnInit(): void {
    this.userStoreService.user$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (user) => {
          this.user.set(user);
        }
    });
    this.userService.fetchUserInformation()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe();
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
