import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ItemDto } from "./item.model";
import { environment } from "src/environment/environment";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class ShopService {

    constructor(private http: HttpClient) {}

    fetchItems(): Observable<ItemDto[]> {
      return this.http.get<ItemDto[]>(environment.backendPath + 'api/shop/items');
    }
}