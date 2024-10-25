import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { SnackbarService } from 'src/app/utils/ui/snackbar/snackbar.service';
import { ItemComponent } from '../shop/item/item.component';
import { LastTasksComponent } from './last-tasks/last-tasks.component';
import { PlayerItemDto, UserResponse } from './user.model';
import { UserService } from './user.service';
import { UserStoreService } from './user.store.service';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss'],
  standalone: true,
  imports: [NgIf, LastTasksComponent, NgFor, ItemComponent],
})
export class PlayerComponent implements OnInit {
  user = signal(null);
  items = null;
  itemLoading = false;

  constructor(private userService: UserService, private snackbarService: SnackbarService, private readonly userStoreService: UserStoreService) {}

  ngOnInit(): void {
    this.userService.fetchPlayerItems().subscribe({
      next: (items: PlayerItemDto[]) => {
        this.items = items;
      },
    });

    this.userStoreService.user$.subscribe({
        next: (user: UserResponse) => {
          this.user.set(user);
        }, error: (error) => {
        }
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
