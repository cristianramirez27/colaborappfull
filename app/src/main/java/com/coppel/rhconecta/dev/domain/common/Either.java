package com.coppel.rhconecta.dev.domain.common;

/**
 *
 *
 * @param <L>
 * @param <R>
 */
public class Either<L, R> {

    /** */
    public Either() { }

    /**
     * Represents the left side of [Either] class which by convention is a "Failure".
     */
    public class Left extends Either<L, R> {

        /* */
        public L left;

        /**
         *
         * @param left
         */
        public Left(L left){
            this.left = left;
        }

    }

    /**
     * Represents the right side of [Either] class which by convention is a "Success".
     */
    public class Right extends Either<L, R> {

        /* */
        public R right;

        /**
         *
         * @param right
         */
        public Right(R right){
            this.right = right;
        }

    }

    /**
     * Returns true if this is a Right, false otherwise.
     * @see Right
     */
    public boolean isRight() {
        try {
            Right r = ((Right) this);
            return true;
        } catch (Exception ignore){
            return false;
        }
    }

    /**
     * Returns true if this is a Left, false otherwise.
     * @see Left
     */
    public boolean isLeft() {
        try {
            Left l = ((Left) this);
            return true;
        } catch (Exception ignore){
            return false;
        }
    }

    /**
     * Creates a Left type.
     * @see Left
     */
    public Left left(L l) {
        return new Left(l);
    }

    /**
     * Creates a Right type.
     * @see Right
     */
    public Right right(R r) {
        return new Right(r);
    }

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     * @see Left
     * @see Right
     */
    public void fold(Fn<L> fnL, Fn<R> fnR){
        try {
            if(isLeft()){
                Left l = ((Left) this);
                fnL.execute(l.left);
            }
            if(isRight()){
                Right r = ((Right) this);
                fnR.execute(r.right);
            }
        } catch (Exception ignore){ }
    }

    /**
     *
     *
     * @param <L>
     */
    public interface Fn<L> {

        /**
         *
         * @param value
         */
        void execute(L value);

    }

}
