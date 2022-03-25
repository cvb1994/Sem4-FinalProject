import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaymentComponent } from './component/payment/payment.component';
import { ReturnPageFailedComponent } from './component/return-page-failed/return-page-failed.component';
import { ReturnPageComponent } from './component/return-page/return-page.component';

const routes: Routes = [
  {path: 'payment/:userId', component: PaymentComponent},
  {path: 'returnPage', component: ReturnPageComponent},
  {path: 'returnPageFailed', component: ReturnPageFailedComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    initialNavigation: 'enabledBlocking'
})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
