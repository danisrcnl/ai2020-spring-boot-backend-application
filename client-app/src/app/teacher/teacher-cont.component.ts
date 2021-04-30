import { Component, OnInit, Input, ViewChild} from '@angular/core';
import {Student} from './student.model'
import { Routes, RouterModule, Router } from '@angular/router';
import { PageNotFoundComponentComponent } from '../page-not-found-component/page-not-found-component.component';

import { VmsContcomponentComponent } from './vms-contcomponent.component';
import {MatTableDataSource} from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import {StudentService} from 'app/services/student.service'
import {FormControl, Validators} from '@angular/forms';
import { Course } from '../model/course.model';
import { ActivatedRoute } from '@angular/router';
import { TeacherComponent } from './teacher.component';
import { TeamService } from 'app/services/team.service';
import { AuthService } from 'app/auth/authservices/auth.service';
import { CourseService } from 'app/services/course.service';
import { StudentDTO } from 'app/model/studentDTO.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'teacher-cont',
  templateUrl: './teacher-cont.component.html',
  styleUrls: ['./teacher-cont.component.css']
})
export class TeacherContComponent implements OnInit {

  studenti: StudentDTO[] = new Array<StudentDTO>();
  studentinoninteam : StudentDTO[] = new Array<StudentDTO>();
  studenti$ : Observable<StudentDTO[]>;
  enrolledstudents : StudentDTO[] = new Array<StudentDTO>();
  studenteaggiunto : StudentDTO;
  darimuovere : StudentDTO[] =new Array<StudentDTO>();
  dataSource = new MatTableDataSource<StudentDTO>(this.enrolledstudents);
  selection = new SelectionModel<StudentDTO>(true, []);
  courseId :string;
  id2: number;
  href : string ="";
  courses: Course[];
  
  @ViewChild(TeacherComponent)
  studentsComponent: TeacherComponent
  
  constructor (private courseService : CourseService, private route: ActivatedRoute, private studentservice : StudentService, private teamservice : TeamService, private router: Router, private activeRoute: ActivatedRoute, private authService: AuthService) 
    
    {
    this.activeRoute.paramMap.subscribe(params => {

    this.href = this.router.url;
     
      this.route.queryParams.subscribe(params => { this.courseId = params.name
      
      
      
       this.courseId.replace('%20', " ");
       console.log (this.courseId);
       });

      

    });
      
    
      this.courseService.getenrolledStudents(this.courseId).subscribe(receivedstudents=>{
        receivedstudents.forEach(s1 => {

            this.enrolledstudents.push(s1);
    
                  
          this.dataSource = new MatTableDataSource<StudentDTO>(this.enrolledstudents);
         console.log("Studenti non iscritti al corso: ",this.studenti);
          this.studentsComponent.updateFilteredOptions();
  
          }
          
        ) 
        
         })

      this.courseService.getfreeStudents(this.courseId).subscribe(data => {

        data.forEach(s => {
          this.studentinoninteam.push(s);
        })

        this.studentsComponent.updateFilteredOptions();
      });
        
      this.studenti$ =  this.courseService.getfreeStudents(this.courseId);

         
  
        };
  ngOnInit() {   



    }

  


    
  
  




  receivestudent($event) {
    this.studenteaggiunto = $event;
    if (!this.enrolledstudents.includes(this.studenteaggiunto)){
    this.enrolledstudents.push(this.studenteaggiunto);
    this.enrolledstudents = Object.assign( this.enrolledstudents);
  


      this.courseService.enrollOne(this.courseId,this.studenteaggiunto).subscribe(data => {console.log(data)
      this.studenti$=this.courseService.getfreeStudents(this.courseId);
      this.studenti$.subscribe(data => {
        console.log(data);
      this.studentsComponent.updateFilteredOptions();
      this.studentsComponent.selection.clear();
      this.studentsComponent.studenteselezionato = null;
      });
    
    }
      );
      
      this.dataSource = new MatTableDataSource<StudentDTO>(this.enrolledstudents);
    
  }}

  //funzione per cancellare uno o più studenti dal corso 
  receivearray ($event) {

    this.darimuovere = $event;
    
      
     this.darimuovere.forEach(s => {

      this.courseService.deleteOne(this.courseId,s.id).subscribe(data => {console.log(data)
     // this.studenti$ =  this.courseService.getfreeStudents(this.courseId); 
      this.studentsComponent.updateFilteredOptions();     

     
      let index: number = this.enrolledstudents.findIndex(d => d === s);
      this.enrolledstudents.splice(index,1);
      console.log(this.enrolledstudents);
       this.dataSource = new MatTableDataSource<StudentDTO>(this.enrolledstudents);
  
       
    },
      
      ),
      
      error => { 


      }
      ;
     })

      this.dataSource = new MatTableDataSource<StudentDTO>(this.enrolledstudents);

    
  }



    
  }

  

