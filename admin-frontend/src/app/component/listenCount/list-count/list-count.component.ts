import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ListenCountService } from 'src/app/service/listen-count.service';
// import {MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {MatDatepicker} from '@angular/material/datepicker';
// import * as _moment from 'moment';
// import {default as _rollupMoment, Moment} from 'moment';

// const moment = _rollupMoment || _moment;

export const MY_FORMATS = {
  parse: {
    dateInput: 'MM/YYYY',
  },
  display: {
    dateInput: 'MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-list-count',
  templateUrl: './list-count.component.html',
  styleUrls: ['./list-count.component.css'],
  providers: [
    // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
    // application's root module. We provide it at the component level here, due to limitations of
    // our example generation script.
  
    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
  ],
})
export class ListCountComponent implements OnInit {
  public listCount : any;
  public currentPage : any = 0;
  previousPageDisable : any = false;
  nextPageDisable : any = false;
  
  // chosenYearHandler(normalizedYear: Moment) {
  //   const ctrlValue = this.formSearch.get('date')?.value;
  //   ctrlValue.year(normalizedYear.year());
  //   this.formSearch.get('date')?.setValue(ctrlValue);
  // }

  // chosenMonthHandler(normalizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
  //   const ctrlValue = this.formSearch.get('date')?.value;
  //   ctrlValue.month(normalizedMonth.month());
  //   this.formSearch.get('date')?.setValue(ctrlValue);
  //   datepicker.close();
  // }

  formSearch = new FormGroup({
    // date: new FormControl(moment())
  });

  constructor(private countSer : ListenCountService) { }

  ngOnInit(): void {
    var formData = new FormData();
    this.loadListData(formData);
  }

  public loadListData(form:any){
    this.countSer.getList(form).subscribe((data) =>{
      this.listCount = data.content.content;
      this.currentPage = data.content.page;
      if(data.content.first == true && data.content.last == true){
        this.previousPageDisable = true;
        this.nextPageDisable = true;
      } else if(data.content.first == false && data.content.last == false){
        this.previousPageDisable = false;
        this.nextPageDisable = false;
      } else if(data.content.first == true){
        this.previousPageDisable = true;
        this.nextPageDisable = false;
      } else if(data.content.last == true){
        this.previousPageDisable = false;
        this.nextPageDisable = true;
      }
    });
  }

  onSubmit() {
    console.log(this.formSearch.get('date')?.value.format('M-YYYY'));
    const searchForm = new FormData();
    searchForm.append('month', this.formSearch.get('date')?.value.format('M'));
    searchForm.append('year', this.formSearch.get('date')?.value.format('YYYY'));
    this.loadListData(searchForm);
  }

  public nextPage(){
    this.currentPage = this.currentPage + 1;
    this.changePage(this.currentPage);
  }

  public previusPage(){
    this.currentPage = this.currentPage - 1;
    this.changePage(this.currentPage);
  }

  public changePage(page:any){
    var form = new FormData();
    form.append('page', page);
    this.loadListData(form);
  }

}
