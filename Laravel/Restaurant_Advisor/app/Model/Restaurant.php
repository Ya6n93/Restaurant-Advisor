<?php

namespace App\Model;

use App\User;
use Illuminate\Database\Eloquent\Model;

class Restaurant extends Model
{
  protected $table = 'restaurant';

    public function user() {
        return $this->hasMany(User::class);
    }
}