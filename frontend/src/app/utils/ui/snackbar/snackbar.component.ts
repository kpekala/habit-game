import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SnackbarData, SnackbarService } from './snackbar.service';

const SNACKBAR_LIFTIME_MILLIS = 10000;

@Component({
  selector: 'app-snackbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './snackbar.component.html',
  styleUrls: ['./snackbar.component.scss']
})
export class SnackbarComponent implements OnInit{
  showing = false;
  label = '';
  iconName = 'info'
  iconColor = 'blue';
  timeout = undefined;

  constructor(private snackbarService: SnackbarService) {

  }

  ngOnInit(): void {
    this.snackbarService.$newSnackbar.subscribe({
      next: (data: SnackbarData) => {
        this.createSnackbar(data);
      }
    });
  }
  createSnackbar(data: SnackbarData) {
    this.label = data.text;
    if(data.type === 'info') {
      this.iconName = 'info';
      this.iconColor = 'blue';
    } else if(data.type === 'success') {
      this.iconName = 'check_circle';
      this.iconColor = 'green';
    } else if(data.type === 'error') {
      this.iconName = 'error';
      this.iconColor = 'red';
    }

    this.showing = true;
    
    if(this.timeout){
      clearTimeout(this.timeout);
    }

    this.timeout = setTimeout(() => {
      this.showing = false;
    }, SNACKBAR_LIFTIME_MILLIS)
  }

  onCloseClick(){ 
    this.showing = false;
  }
}

