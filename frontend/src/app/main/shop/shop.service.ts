import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { environment } from 'src/environments/environment';
import { ItemDto } from './item.model';

@Injectable({ providedIn: 'root' })
export class ShopService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  fetchItems(): Observable<ItemDto[]> {
    return this.http.get<ItemDto[]>(environment.backendPath + 'api/shop/items');
  }

  buyItem(item: ItemDto): Observable<string> {
    const buyItemRequest = {
      email: this.authService.getEmail(),
      itemId: item.id,
    };

    return this.http.post<any>(
      environment.backendPath + 'api/shop/buy',
      buyItemRequest
    );
  }
}
