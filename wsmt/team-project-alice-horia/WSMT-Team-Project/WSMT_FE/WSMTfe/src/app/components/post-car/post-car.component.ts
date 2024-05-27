import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CarService } from 'src/app/service/car.service';

@Component({
  selector: 'app-post-car',
  templateUrl: './post-car.component.html',
  styleUrls: ['./post-car.component.css']
})
export class PostCarComponent  {

  postCarForm!: FormGroup;
  errorMessage!: string;

  constructor(private carService: CarService, 
    private fb: FormBuilder,
    private router : Router) { }

  ngOnInit() {
    this.postCarForm = this.fb.group({
      model : [null, [Validators.required]],
      manufacturer : [null, [Validators.required]],
      productionYear : [null, [Validators.required]]
    })
  }

  postCar() {
    console.log(this.postCarForm.value);
    this.carService.postCar(this.postCarForm.value).subscribe((res) =>{
      console.log(res);
      this.router.navigateByUrl("");
    })
  }
}
