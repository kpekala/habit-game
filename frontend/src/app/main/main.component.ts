import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './header/header.component';
import { SnackbarComponent } from '../utils/ui/snackbar/snackbar.component';

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss'],
    standalone: true,
    imports: [HeaderComponent, RouterOutlet, SnackbarComponent]
})
export class MainComponent {

}
