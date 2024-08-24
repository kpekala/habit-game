import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { AuthResponse } from 'src/app/auth/dtos/auth-response.model';
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
  imports: [NgIf, LastTasksComponent, NgFor, ItemComponent],
})
export class PlayerComponent implements OnInit {
  isPlayerInfoLoaded = false;
  userInfo: UserResponse;
  items = null;
  itemLoading = false;

  constructor(
    private userService: UserService,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.userService.fetchPlayerItems().subscribe({
      next: (items: PlayerItemDto[]) => {
        this.items = items;
      },
    });

    this.userService.$updatePlayer.subscribe({
      next: () => {
        this.updateUserInformation();
      },
    });

    this.updateUserInformation();
  }

  updateUserInformation() {
    this.userService.fetchUserInformation().subscribe({
      next: (response: UserResponse) => {
        this.userInfo = response;
        this.isPlayerInfoLoaded = true;
      },
      error: (msg) => {
        this.isPlayerInfoLoaded = false;
      },
    });
  }

  onItemClick(item: PlayerItemDto) {
    this.itemLoading = true;
    this.userService.useItem(item.id).subscribe({
      next: () => {
        this.itemLoading = false;
        this.snackbarService.success('Item succesfully used!');
        this.updateItemsList(item);
        this.userService.updatePlayer();
      },
      error: () => {
        this.itemLoading = false;
        this.snackbarService.error('Error when using item!');
      },
    });
  }

  updateItemsList(item: PlayerItemDto) {
    if (item.count > 1)
      this.items.filter((i) => i.name === item.name)[0].count -= 1;
    else this.items = this.items.filter((i) => i.name !== item.name);
  }
}
