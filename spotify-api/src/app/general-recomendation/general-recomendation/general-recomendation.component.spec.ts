import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneralRecomendationComponent } from './general-recomendation.component';

describe('GeneralRecomendationComponent', () => {
  let component: GeneralRecomendationComponent;
  let fixture: ComponentFixture<GeneralRecomendationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GeneralRecomendationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GeneralRecomendationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
