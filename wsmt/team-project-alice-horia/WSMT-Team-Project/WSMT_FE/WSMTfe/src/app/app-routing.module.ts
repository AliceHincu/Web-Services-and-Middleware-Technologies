import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostCarComponent } from './components/post-car/post-car.component';
import { GetAllCarsComponent } from './components/get-all-cars/get-all-cars.component';
import { UpdateCarComponent } from './components/update-car/update-car.component';

const routes: Routes = [
  {path : 'car', component : PostCarComponent},
  {path : "", component : GetAllCarsComponent},
  {path : "car/:id", component : UpdateCarComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
