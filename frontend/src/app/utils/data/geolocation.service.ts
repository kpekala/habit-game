import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

const geoOptions = {
    enableHighAccuracy: true,
  };

@Injectable({providedIn: 'root'})
export class GeolocationService {
  public getGeolocation(): Observable<GeolocationPosition> {
    return new Observable((subscriber) => {
      navigator.geolocation.getCurrentPosition(
        (position) => subscriber.next(position), 
        (error) => subscriber.error(error), 
        geoOptions
      );
    });
  }
}