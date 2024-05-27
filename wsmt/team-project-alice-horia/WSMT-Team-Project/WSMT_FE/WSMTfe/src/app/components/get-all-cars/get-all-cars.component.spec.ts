import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAllCarsComponent } from './get-all-cars.component';

describe('GetAllCarsComponent', () => {
  let component: GetAllCarsComponent;
  let fixture: ComponentFixture<GetAllCarsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetAllCarsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetAllCarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
