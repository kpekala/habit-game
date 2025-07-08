import { Component } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import { ServiceWorkerModule } from '@angular/service-worker'
import { environment } from 'src/environments/environment'

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    standalone: true,
    imports: [RouterOutlet],
})
export class AppComponent {
    title = 'frontend'
}
