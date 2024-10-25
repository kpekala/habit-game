import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-loading-circle',
    templateUrl: './loading-circle.component.html',
    styleUrls: ['./loading-circle.component.scss'],
    standalone: true
})
export class LoadingCircleComponent {
  @Input() classes;
}
