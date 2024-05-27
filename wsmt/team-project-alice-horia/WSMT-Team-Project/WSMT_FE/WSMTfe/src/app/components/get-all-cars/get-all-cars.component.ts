import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CarService } from 'src/app/service/car.service';

@Component({
  selector: 'app-get-all-cars',
  templateUrl: './get-all-cars.component.html',
  styleUrls: ['./get-all-cars.component.css']
})
export class GetAllCarsComponent {

  cars: any = [];

  constructor(private carService : CarService,
    private router : Router
  ) { }

  ngOnInit() {
    this.getAllCars();
  }

  getAllCars(){
    this.carService.getAllCars().subscribe((res)=>{
      console.log(res);
      this.cars = res;
    })
  }

  deleteCar(id : number){
    this.carService.deleteCar(id).subscribe((res)=>{
      console.log(res)
      this.getAllCars()
    })
  }
}
