import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { AuthResponse } from 'src/app/auth/auth-response.model';
import { UserResponse } from './user.model';
import { LastTasksComponent } from './last-tasks/last-tasks.component';
import { NgIf } from '@angular/common';

@Component({
    selector: 'app-player',
    templateUrl: './player.component.html',
    styleUrls: ['./player.component.scss'],
    standalone: true,
    imports: [NgIf, LastTasksComponent]
})
export class PlayerComponent implements OnInit{
  isPlayerInfoLoaded = false;
  userInfo: UserResponse;

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
          console.log(msg);
          this.isPlayerInfoLoaded = false;
        }
      })
  }

}
