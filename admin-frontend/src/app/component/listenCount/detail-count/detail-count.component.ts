import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListenCountService } from 'src/app/service/listen-count.service';

@Component({
  selector: 'app-detail-count',
  templateUrl: './detail-count.component.html',
  styleUrls: ['./detail-count.component.css']
})
export class DetailCountComponent implements OnInit {
  public listSong : any;
  public countId:any;

  constructor(
    private countSer : ListenCountService,
    private _Activatedroute:ActivatedRoute
    ) { }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => {
      this.countId = params.get('countId');
      if(this.countId != null){
        this.countSer.getById(this.countId).subscribe((data) =>{
          console.log(data);
          this.listSong = data.content.listSong;
        })
      }
    })

  }

}
