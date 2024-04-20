import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { AuthResponse } from 'src/app/auth/auth-response.model';
import { PlayerItemDto, UserResponse } from './user.model';
import { LastTasksComponent } from './last-tasks/last-tasks.component';
import { NgFor, NgIf } from '@angular/common';
import { takeUntil } from 'rxjs';
import { ItemComponent } from '../shop/item/item.component';

@Component({
    selector: 'app-player',
    templateUrl: './player.component.html',
    styleUrls: ['./player.component.scss'],
    standalone: true,
    imports: [NgIf, LastTasksComponent, NgFor, ItemComponent]
})
export class PlayerComponent implements OnInit{
  isPlayerInfoLoaded = false;
  userInfo: UserResponse;
  items = null;

  constructor(private userService: UserService) {

  }

  ngOnInit(): void {
    this.userService.fetchUserInformation()
      .subscribe({
        next: (response: UserResponse) => {
          this.userInfo = response;
          this.isPlayerInfoLoaded = true;
        },
        error: (msg) => {
          this.isPlayerInfoLoaded = false;
        }
      });

    this.userService.fetchPlayerItems()
      .subscribe({
        next: (items: PlayerItemDto[]) => {
          this.items = items;
        }
      })
  }

}
