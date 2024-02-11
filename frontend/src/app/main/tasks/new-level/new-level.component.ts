import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-new-level',
    templateUrl: './new-level.component.html',
    styleUrls: ['./new-level.component.scss'],
    standalone: true
})
export class NewLevelComponent {

  @Input() level: number;
}
