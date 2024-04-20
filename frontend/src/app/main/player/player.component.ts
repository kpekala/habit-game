import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { AuthResponse } from 'src/app/auth/auth-response.model';
import { PlayerItemDto, UserResponse } from './user.model';
import { LastTasksComponent } from './last-tasks/last-tasks.component';
import { NgFor, NgIf } from '@angular/common';
import { takeUntil } from 'rxjs';
import { ItemComponent } from '../shop/item/item.component';
import { SnackbarService } from 'src/app/utils/snackbar/snackbar.service';

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
  itemLoading = false;

  constructor(private userService: UserService, private snackbarService: SnackbarService) {

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

  onItemClick(item: PlayerItemDto) {
    this.itemLoading = true;
    this.userService.useItem(item.id)
      .subscribe({
        next: () => {
          this.itemLoading = false;
          this.snackbarService.success('Item succesfully used!');
        },
        error: () => {
          this.itemLoading = false;
          this.snackbarService.error('Error when using item!');
        }
      });
  }

}
