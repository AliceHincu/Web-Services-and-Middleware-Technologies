import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { CarService } from 'src/app/service/car.service';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrls: ['./update-car.component.css']
})
export class UpdateCarComponent {

  errorMessage!: string;
  updateCarForm! : FormGroup;
  id : number =this.activatedRoute.snapshot.params["id"];

  constructor(private activatedRoute: ActivatedRoute,
    private service : CarService,
    private fb : FormBuilder,
    private router : Router
  ) { }

  ngOnInit() {
    this.updateCarForm = this.fb.group({
      model : [null, [Validators.required]],
      manufacturer : [null, [Validators.required]],
      productionYear : [null, [Validators.required]]
    })
    this.getCarById(this.id)
  }

  getCarById(id : number){
    this.service.getCarById(this.id).subscribe((res) => {
      console.log(res)
      this.updateCarForm.patchValue(res);
    })
  }

  updateCar(){
    this.service.updateCar(this.id,this.updateCarForm.value).subscribe((res) => {
      console.log(res);
      if (res.id != null){
        this.router.navigateByUrl("");
      }
    })
  }
}
