import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SnackbarData, SnackbarService } from './snackbar.service';

@Component({
  selector: 'app-snackbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './snackbar.component.html',
  styleUrls: ['./snackbar.component.scss']
})
export class SnackbarComponent implements OnInit{
  showing = false;
  label = 'You succesfully bought a potion!';

  constructor(private snackbarService: SnackbarService) {

  }

  ngOnInit(): void {
    this.snackbarService.$newSnackbar.subscribe({
      next: (data: SnackbarData) => {
        this.label = data.text;
      }
    });
  }
}
