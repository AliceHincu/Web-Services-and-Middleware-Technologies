import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASIC_URL = ["http://localhost:8080"]

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient) { }

  postCar(car: any) : Observable<any> {
    return this.http.post(BASIC_URL+"/api/car",car)
  }

  getAllCars() : Observable<any> {
    return this.http.get(BASIC_URL+"/api/cars")
  }

  getCarById(id : number) : Observable<any> {
    return this.http.get(BASIC_URL+"/api/car/"+id)
  }

  updateCar(id : number, car : any) : Observable<any> {
    return this.http.put(BASIC_URL+"/api/car/"+id, car)
  }

  deleteCar(id : number) : Observable<any> {
    return this.http.delete(BASIC_URL+"/api/car/"+id)
  }
}
